package com.cloverleaf.navigator.chart.layout;

import com.carrotsearch.hppc.IntObjectHashMap;
import com.carrotsearch.hppc.IntObjectMap;
import com.carrotsearch.hppc.procedures.IntObjectProcedure;
import com.cloverleaf.navigator.chart.ToFloatFunction;

import java.util.*;
import java.util.function.*;

public class LayoutGrid implements ILayout {

    private static final ILayoutConstraint[] EMPTY_CONSTRAINTS = {};
    private static final float[] EMPTY_SIZES = {};

    private ILayoutConstraint[] rowConstraints = EMPTY_CONSTRAINTS;
    private ILayoutConstraint[] columnConstraints = EMPTY_CONSTRAINTS;

    private float[] rowSizes = EMPTY_SIZES;
    private float[] columnSizes = EMPTY_SIZES;

    private final IntObjectMap<ILayoutElement> elements = new IntObjectHashMap<>();

    public void setRowConstraints(ILayoutConstraint... c) {
        rowConstraints = checkConstraints(c);
    }

    private ILayoutConstraint[] checkConstraints(ILayoutConstraint[] c) {
        Objects.requireNonNull(c);
        for(ILayoutConstraint ic : c)
            Objects.requireNonNull(ic);

        return c;
    }

    public void setColumnConstraints(ILayoutConstraint... c) {
        rowConstraints = checkConstraints(c);
    }

    public void setElement(int gx, int gy, ILayoutElement e) {
        elements.put(GridPoint.encode(gx, gy), e);
    }

    @Override
    public void layout(int width, int height) {
        GridAxis vertical = createElementIndex(rowConstraints);
        GridAxis horizontal = createElementIndex(columnConstraints);

        fillSpacerAmounts(vertical, rowConstraints);
        fillSpacerAmounts(horizontal, columnConstraints);

        // todo - this is buggy
//        fillMeasuredAmounts(vertical, rowConstraints,
//                new GridMeasurer(horizontal, GridPoint::encode, ILayoutElement::measureWidth));
//        fillMeasuredAmounts(horizontal, rowConstraints,
//                new GridMeasurer(vertical, (y, x) -> GridPoint.encode(x, y), ILayoutElement::measureHeight));

        fillWeightedAmounts(vertical, rowConstraints, height);
        fillWeightedAmounts(horizontal, columnConstraints, width);

        // todo
//        float topX, topY;
//        for (int i = 0; i < vertical.elementIndex.length; i++) {
//            topX = 0;
//            for (int j = 0; j < horizontal.elementIndex.length; j++) {
//
//            }
//            topY += vertical.amounts[i];
//        }

    }

    private GridAxis createElementIndex(ILayoutConstraint[] constraints) {
        int constraintCount = constraints != null ? constraints.length : 0;
        GridAxis r = new GridAxis(constraintCount);
        int gridIndex = 0;
        for(int i = 0; i < constraintCount; i++) {
            ILayoutConstraint c = constraints[i];
            if(c instanceof ILayoutConstraint.Space) {
                r.elementIndex[i] = -1;
            } else {
                r.elementIndex[i] = gridIndex++;
            }
        }
        return r;
    }

    private void fillSpacerAmounts(GridAxis target, ILayoutConstraint[] constraints) {
        for(int i = 0; i < constraints.length; i++) {
            if(!(constraints[i] instanceof ILayoutConstraint.Space))
                continue;

            target.amounts[i] = ((ILayoutConstraint.Space)constraints[i]).getAmount();
        }
    }

    private void fillMeasuredAmounts(GridAxis target, ILayoutConstraint[] constraints, GridMeasurer measurer) {
        for(int i = 0; i < constraints.length; i++) {
            if(!(constraints[i] instanceof ILayoutConstraint.Measure))
                continue;

            target.amounts[i] = measurer.maxOfMins(i, elements);
        }
    }

    static class GridMeasurer {
        final GridAxis runnigAxis;
        final IntBinaryOperator indexer;
        final ToFloatFunction<ILayoutElement> measure;

        GridMeasurer(GridAxis runnigAxis, IntBinaryOperator indexer, ToFloatFunction<ILayoutElement> measure) {
            this.runnigAxis = runnigAxis;
            this.indexer = indexer;
            this.measure = measure;
        }

        float maxOfMins(int fixedAxisPoint, IntObjectMap<ILayoutElement> elements) {
            float max = 0;
            final int l = runnigAxis.elementIndex.length;
            for (int i = 0; i < l; i++) {
                if (runnigAxis.elementIndex[i] == -1)
                    continue;

                int gxgy = indexer.applyAsInt(i, fixedAxisPoint);
                ILayoutElement e = elements.get(gxgy);
                if (e == null)
                    continue;

                float size = measure.applyAsFloat(e);
                if (size > max)
                    max = size;
            }
            return max;
        }
    }

    private void fillWeightedAmounts(GridAxis target, ILayoutConstraint[] constraints, float amount) {
        float normalizer = 1;
        for (ILayoutConstraint constraint : constraints) {
            if (!(constraint instanceof ILayoutConstraint.Weight))
                continue;

            normalizer += ((ILayoutConstraint.Weight) constraint).getWeight();
        }

        float remainingAmount = amount;
        for(float size : target.amounts)
            remainingAmount -= size;

        if(remainingAmount <= 0 || normalizer == 0)
            return; // nothing to distribute or not a distribution

        for(int i = 0; i < constraints.length; i++) {
            if(!(constraints[i] instanceof  ILayoutConstraint.Weight))
                continue;
            target.amounts[i] = amount * (((ILayoutConstraint.Weight)constraints[i]).getWeight() / normalizer);
        }
    }

    static class GridAxis {
        final int[] elementIndex;
        final float[] amounts;

        GridAxis(int count) {
            this.elementIndex = new int[count];
            this.amounts = new float[count];
        }
    }

    @Override
    public String toString() {
        return "LayoutGrid(" + rowConstraints.length + " x " + columnConstraints.length+ ")";
    }

    static class GridPoint {

        static int encode(int gx, int gy) {
            if(gx < 0 || Short.MAX_VALUE < gx)
                throw new IllegalArgumentException("Invalid GridPoint.x value " + gx + ". Valid range: 0..32767");
            if(gy < 0 || Short.MAX_VALUE < gy)
                throw new IllegalArgumentException("Invalid GridPoint.y value " + gy + ". Valid range: 0..32767");

            return (gx << 16) | (gy & 0xffff);
        }

        static int getX(int encoded) {
            return (encoded >> 16) & 0xffff;
        }

        static int getY(int encoded) {
            return encoded & 0xffff;
        }

        public static  String toString(int encoded)
        {
            return "(" + getX(encoded) + ", " + getY(encoded) + ")";
        }
    }
}

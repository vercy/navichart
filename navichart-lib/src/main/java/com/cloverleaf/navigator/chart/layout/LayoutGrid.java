package com.cloverleaf.navigator.chart.layout;

import com.carrotsearch.hppc.IntObjectHashMap;
import com.carrotsearch.hppc.IntObjectMap;
import com.carrotsearch.hppc.cursors.IntObjectCursor;
import com.carrotsearch.hppc.procedures.IntObjectProcedure;

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
        Distribution vertical = distribute(width, rowConstraints, y -> xy -> GridPoint.getY(xy) == y);
        Distribution horizontal = distribute(height, columnConstraints, x -> xy -> GridPoint.getX(xy) == x);

        // todo
//        elements.forEach((IntObjectProcedure<? super ILayoutElement>) (idx, e) -> {
//            e.setSize(
//                    horizontal[GridPoint.getX(idx)],
//                    vertical[GridPoint.getY(idx)]);
//        });
    }

    private Distribution distribute(float amount, ILayoutConstraint[] constraints, IntFunction<IntPredicate> measureIndexer) {
        int constraintCount = constraints != null ? constraints.length : 0;
        Distribution r = new Distribution(constraintCount);

        float weightNormalizer = 1;
        int gridIndex = 0;
        for(int i = 0; i < constraintCount; i++) {
            ILayoutConstraint c = constraints[i];
            if(c instanceof ILayoutConstraint.Space) {
                r.amounts[i] = ((ILayoutConstraint.Space)c).getAmount();
                r.grid[i] = -1;
            } else if(c instanceof ILayoutConstraint.Measure) {
                r.grid[i] = gridIndex++;
                r.amounts[i] = measure(measureIndexer.apply(r.grid[i]));
            } else if(c instanceof  ILayoutConstraint.Weight) {
                weightNormalizer += ((ILayoutConstraint.Weight)c).getWeight();
                r.grid[i] = gridIndex++;
            }
        }

        float remainingAmount = amount;
        for(float size : r.amounts)
            remainingAmount -= size;

        if(remainingAmount > 0 && weightNormalizer > 0) {
            for(int i = 0; i < constraintCount; i++) {
                ILayoutConstraint c = constraints[i];
                if(c instanceof  ILayoutConstraint.Weight) {
                    r.amounts[i] = amount * (((ILayoutConstraint.Weight)c).getWeight() / weightNormalizer);
                }
            }
        }

        return r;
    }

    static class Distribution {
        final int[] grid;
        final float[] amounts;

        Distribution(int count) {
            this.grid = new int[count];
            this.amounts = new float[count];
        }
    }

    private float measure(IntPredicate indexMatcher) {
//        float maxOfMins = 0;
//        for(IntObjectCursor<ILayoutElement> c : elements) {
//            if(indexMatcher.test(c.key)) {
//                c.value.
//            }
//        }
        // todo
        return 0;
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

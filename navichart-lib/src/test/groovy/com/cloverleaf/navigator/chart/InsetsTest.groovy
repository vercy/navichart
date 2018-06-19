package com.cloverleaf.navigator.chart

import spock.lang.Specification
import spock.lang.Unroll

import java.awt.geom.Rectangle2D

@Unroll
class InsetsTest extends Specification {

    def "should implement object interface"() {
        def i1 = new Insets()
        def i2 = new Insets(i1)
        def i3 = new Insets(1, 0, 0, 0)
        def i4 = new Insets(0, 1, 0, 0)
        def i5 = new Insets(0, 0, 1, 0)
        def i6 = new Insets(0, 0, 0, 1)

    expect:
        i1 == i1 && i1 != new Object()
        i1.hashCode() == i2.hashCode() && i1 == i2
        i1.hashCode() != i3.hashCode() && i1 != i3
        i1.hashCode() != i4.hashCode() && i1 != i4
        i1.hashCode() != i5.hashCode() && i1 != i5
        i1.hashCode() != i6.hashCode() && i1 != i6

        i1.left == 0
        i1.right== 0
        i1.top == 0
        i1.bottom == 0
        i1.relativeTo == null

        i1.toString() == "{left=0, top=0, bottom=0, right=0}"
    }

    def "areaLeft() from #insets"() {
        insets.setRelativeTo(inSize)
    expect:
        insets.getAreaLeft() == outSize

    where:
        insets                     | inSize             | outSize
        new Insets()               | new Size(100, 100) | new Size(100, 100)
        new Insets(10, 20, 30, 40) | new Size(100, 100) | new Size(50, 50)
        new Insets(10, 20, 30, 90) | new Size(100, 100) | new Size(0, 0)
    }

    def "areaLeft(size) from #insets"() {
        expect:
        insets.getAreaLeft(inSize) == outRect

        where:
        insets | inSize | outRect
        new Insets()               | new Size(100, 100) | new Rectangle2D.Float(0, 0, 100, 100)
        new Insets(10, 20, 30, 40) | new Size(100, 100) | new Rectangle2D.Float(10, 20 ,50, 50)
        new Insets(10, 20, 30, 90) | new Size(100, 100) | new Rectangle2D.Float(10, 20, 0, 50)
    }
}

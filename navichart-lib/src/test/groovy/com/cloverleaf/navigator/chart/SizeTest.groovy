package com.cloverleaf.navigator.chart

import spock.lang.Specification

class SizeTest extends Specification {
    def "should implement object interface"() {
        def i1 = new Size(10,20)
        def i2 = new Size(10, 20)
        def i3 = new Size(11,20)
        def i4 = new Size(10,22)


    expect:
        i1.equals(i1) && i1 != new Object()
        i1.hashCode() == i2.hashCode() && i1 == i2
        i1.hashCode() != i3.hashCode() && i1 != i3
        i1.hashCode() != i4.hashCode() && i1 != i4

        i1.width == 10
        i1.height == 20

        i1.toString() == "{width=10, height=20}"
    }

    def "isEmpty should indicates is are is greater than zero"() {
    expect:
        s.empty == isEmpty
    where:
        s | isEmpty
        new Size(10, 20) | false
        new Size(10, 0) | true
        new Size(0, 20) | true
        new Size(-1, -1) | true
    }
}

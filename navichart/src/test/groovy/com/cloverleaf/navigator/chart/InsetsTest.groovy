package com.cloverleaf.navigator.chart

import spock.lang.Specification


class InsetsTest extends Specification {

    def "should implement object interface"() {
        def i1 = new Insets()
        def i2 = new Insets()

        expect:
        i1.hashCode() == i2.hashCode() && i1 == i2

        i1.left == 0
        i1.right== 0
        i1.top == 0
        i1.bottom == 0

        i1.toString() == "{left=0, top=0, bottom=0, right=0}"
    }
}

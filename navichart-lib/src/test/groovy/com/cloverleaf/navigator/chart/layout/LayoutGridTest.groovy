package com.cloverleaf.navigator.chart.layout

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class LayoutGridTest extends Specification {

    def "gridpoint illegal arguments x=#x y=#y"() {
    when:
        LayoutGrid.GridPoint.encode(x, y)
    then:
        thrown IllegalArgumentException
    where:
        x                   | y
        -1                  | 0
        0                   | -1
        Short.MAX_VALUE + 1 | 0
        0                   | Short.MAX_VALUE + 1
    }

    def "gridpoint.encode x=#x y=#y"() {
    expect:
        LayoutGrid.GridPoint.encode(x, y) == expected
    where:
        x               | y               | expected
        0               | 0               | 0
        1               | 1               | 65_537
        Short.MAX_VALUE | 0               | 2_147_418_112
        0               | Short.MAX_VALUE | Short.MAX_VALUE
        Short.MAX_VALUE | Short.MAX_VALUE | 2_147_450_879
    }

    def "gridpoint.decode x=#x y=#y"() {
    expect:
        LayoutGrid.GridPoint.getX(encoded) == x
        LayoutGrid.GridPoint.getY(encoded) == y
        LayoutGrid.GridPoint.toString(encoded) == "($x, $y)"
    where:
        encoded         | x               | y
        0               | 0               | 0
        65_537          | 1               | 1
        2_147_418_112   | Short.MAX_VALUE | 0
        Short.MAX_VALUE | 0               | Short.MAX_VALUE
        2_147_450_879   | Short.MAX_VALUE | Short.MAX_VALUE
    }
}

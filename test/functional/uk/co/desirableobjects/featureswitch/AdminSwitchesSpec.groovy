package uk.co.desirableobjects.featureswitch

import geb.spock.GebSpec
import uk.co.desirableobjects.featureswitch.page.SwitchesPage

class AdminSwitchesSpec extends GebSpec {

    void setup() {
        to SwitchesPage
    }

    def 'a user can visit the switches page'() {

        expect:
            at SwitchesPage
    }

    def 'a user can view the current state of the switches'() {

        expect:
            switches.size() == 3
            switches(0).label.text() == 'alwaysOn'
            switches(0).enabled
            switches(1).label.text() == 'alwaysOff'
            !switches(1).enabled
            switches(2).label.text() == 'toggle-me'
            switches(2).enabled

    }

    def 'a user can modify the state of a switch'() {

        expect:
            switches(2).label.text() == 'toggle-me'
            switches(2).enabled

        when:
            switches(2).toggle.click()

        then:
            at SwitchesPage
            !switches(2).enabled

        when:
            switches(2).toggle.click()

        then:
            at SwitchesPage
            switches(2).enabled

    }

    def 'a user can modify a switch state by hitting a url'() {

        expect:
            switches(2).label.text() == 'toggle-me'
            switches(2).enabled

        when:
            to SwitchesPage, 'toggle', feature: 'toggle-me'

        and:
            to SwitchesPage

        then:
            switches(2).label.text() == 'toggle-me'
            !switches(2).enabled
    }

}

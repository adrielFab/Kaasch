/*
* Class Distance
*
* 03/04/17
*/
package DirectionModel;

public class Distance {

    public int getValue() {

        return value;
    }

    public void setValue(int value) {

        this.value = value;
    }

    public String getText() {

        return text;
    }

    public void setText(String text) {

        this.text = text;
    }

    private String text;
    private int value;

    public Distance(String text, int value) {

        this.text = text;
        this.value = value;
    }
}

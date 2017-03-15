/*
* Class Distance
*
* 03/04/17
*/
package DirectionModel;

public class Distance {

    private String text;
    private int value;

    public Distance(String text, int value) {

        this.text = text;
        this.value = value;
    }

    /**
     * Accessor method for value
     * @return int This returns the value
     */
    public int getValue() {

        return value;
    }

    /**
     * Mutator method for value
     * @param value
     */
    public void setValue(int value) {

        this.value = value;
    }

    /**
     * Accessor method
     * @return String This returns the text
     */
    public String getText() {

        return text;
    }

    /**
     * Mutator method for text
     * @param text
     */
    public void setText(String text) {

        this.text = text;
    }

}

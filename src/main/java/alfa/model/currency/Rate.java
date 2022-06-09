package alfa.model.currency;

public class Rate {
    private final static int NAME_LENGTH = 3;
    private String name;
    private double value;

    public Rate() {
        //empty
    }

    public Rate(String name, double value) throws IllegalArgumentException {
        if (name != null
                && name.length() == NAME_LENGTH
                && name.toUpperCase().equals(name)
                && value > 0){
            this.name = name;
            this.value = value;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object object) {
        //musthave checks
        if (this == object) {
            return true;
        }
        if (object == null || !object.getClass().equals(Rate.class)) {
            return false;
        }

        //field check
        Rate rateObject = (Rate) object;
        if (this.name.equals(rateObject.name)
                && this.value == rateObject.value) {
            return true;
        }
        return false;
    }
}

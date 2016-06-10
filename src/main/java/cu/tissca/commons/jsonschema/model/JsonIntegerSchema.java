package cu.tissca.commons.jsonschema.model;

/**
 * @author ariel.viera@gmail.com (Ariel Viera)
 */
public class JsonIntegerSchema extends JsonNumberSchema {
    private Integer multipleOf;

    public Integer getMultipleOf() {
        return this.multipleOf;
    }

    public void setMultipleOf(Integer multipleOf) {
        this.multipleOf = multipleOf;
    }

    @Override
    public boolean isIntegerSchema() {
        return true;
    }

    @Override
    public JsonIntegerSchema asIntegerSchema() {
        return this;
    }
}

package cu.tissca.commons.jsonschema.model;

/**
 * @author ariel.viera@gmail.com (Ariel Viera)
 */
public class JsonNumberSchema extends JsonSchema {

    private Integer maximum;
    private Integer minimum;
    private Boolean exclusiveMaximum;
    private Boolean exclusiveMinimum;

    public JsonNumberSchema() {
    }

    @Override
    public boolean isNumberSchema() {
        return true;
    }

    @Override
    public JsonNumberSchema asNumberSchema() {
        return this;
    }

    public Integer getMaximum() {
        return this.maximum;
    }

    public Integer getMinimum() {
        return this.minimum;
    }

    public Boolean getExclusiveMaximum() {
        return this.exclusiveMaximum;
    }

    public Boolean getExclusiveMinimum() {
        return this.exclusiveMinimum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }

    public void setExclusiveMaximum(Boolean exclusiveMaximum) {
        this.exclusiveMaximum = exclusiveMaximum;
    }

    public void setExclusiveMinimum(Boolean exclusiveMinimum) {
        this.exclusiveMinimum = exclusiveMinimum;
    }
}

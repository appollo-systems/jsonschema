package cu.tissca.commons.jsonschema.model;

/**
 * @author ariel.viera@gmail.com (Ariel Viera)
 */
public class JsonStringSchema extends JsonSchema {
    private Integer maxLength;
    private Integer minLength;
    private String pattern;

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Integer getMaxLength() {
        return this.maxLength;
    }

    public Integer getMinLength() {
        return this.minLength;
    }

    public String getPattern() {
        return this.pattern;
    }

    @Override
    public boolean isStringSchema() {
        return true;
    }

    @Override
    public JsonStringSchema asStringSchema() {
        return this;
    }
}

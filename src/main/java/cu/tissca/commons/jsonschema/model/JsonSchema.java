package cu.tissca.commons.jsonschema.model;

/**
 * @author ariel.viera@gmail.com (Ariel Viera)
 */
public class JsonSchema {

    private String description;
    private String _default;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isObjectSchema() {
        return false;
    }

    public JsonObjectSchema asObjectSchema() {
        return null;
    }

    public boolean isEnumSchema() {
        return false;
    }

    public JsonEnumSchema asEnumSchema() {
        return null;
    }

    public boolean isIntegerSchema() {
        return false;
    }

    public JsonIntegerSchema asIntegerSchema() {
        return null;
    }

    public boolean isNumberSchema() {
        return false;
    }

    public JsonNumberSchema asNumberSchema() {
        return null;
    }

    public boolean isArraySchema() {
        return false;
    }

    public JsonArraySchema asArraySchema() {
        return null;
    }

    public boolean isStringSchema() {
        return false;
    }

    public JsonStringSchema asStringSchema() {
        return null;
    }

    public boolean isBooleanSchema() {
        return false;
    }

    String getDefault() {
        return this._default;
    }

    public void setDefault(String _default) {
        this._default = _default;
    }

    public boolean isNullSchema() {
        return false;
    }

    public JsonNullSchema asNullSchema(){
        return null;
    }

    public JsonBooleanSchema asBooleanSchema(){
        return null;
    }
}

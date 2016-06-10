package cu.tissca.commons.jsonschema.model;

/**
 * @author ariel.viera@gmail.com (Ariel Viera)
 */
public class JsonBooleanSchema extends JsonSchema {
    @Override
    public boolean isBooleanSchema() {
        return  true;
    }

    @Override
    public JsonBooleanSchema asBooleanSchema() {
        return this;
    }
}

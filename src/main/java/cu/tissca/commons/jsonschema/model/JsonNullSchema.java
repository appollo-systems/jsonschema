package cu.tissca.commons.jsonschema.model;

/**
 * @author ariel.viera@gmail.com (Ariel Viera)
 */
public class JsonNullSchema extends JsonSchema {

    @Override
    public boolean isNullSchema() {
        return true;
    }

    @Override
    public JsonNullSchema asNullSchema() {
        return this;
    }
}

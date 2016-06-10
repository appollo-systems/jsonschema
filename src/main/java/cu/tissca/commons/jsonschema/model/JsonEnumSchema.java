package cu.tissca.commons.jsonschema.model;

import java.util.Set;

/**
 * @author ariel.viera@gmail.com (Ariel Viera)
 */
public class JsonEnumSchema extends JsonSchema {
    private Set<String> _enum;

    public Set<String> getEnum() {
        return this._enum;
    }

    public void setEnum(Set<String> _enum) {
        this._enum = _enum;
    }

    @Override
    public boolean isEnumSchema() {
        return true;
    }

    @Override
    public JsonEnumSchema asEnumSchema() {
        return this;
    }
}

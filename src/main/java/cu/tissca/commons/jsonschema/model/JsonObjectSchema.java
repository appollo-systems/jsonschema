package cu.tissca.commons.jsonschema.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author ariel.viera@gmail.com (Ariel Viera)
 */
public class JsonObjectSchema extends JsonSchema {
    private Map<String, JsonSchema> properties;
    private Set<String> required;

    public Map<String, JsonSchema> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String, JsonSchema> properties) {
        this.properties = properties;
    }

    @Override
    public boolean isObjectSchema() {
        return true;
    }

    @Override
    public JsonObjectSchema asObjectSchema() {
        return this;
    }

    public Set<String> getRequired(){
        return this.required;
    }

    public void setRequired(Set<String> required){
        this.required = new HashSet<>(required);
    }
}

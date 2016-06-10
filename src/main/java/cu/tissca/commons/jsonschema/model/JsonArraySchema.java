package cu.tissca.commons.jsonschema.model;

/**
 * @author ariel.viera@gmail.com (Ariel Viera)
 */
public class JsonArraySchema extends JsonSchema {
    private Integer minItems;
    private Integer maxItems;
    private Boolean uniqueItems;
    private Object items;
    private JsonSchema additionalItems;

    public Integer getMinItems() {
        return this.minItems;
    }

    public void setMinItems(Integer minItems) {
        this.minItems = minItems;
    }

    public Integer getMaxItems() {
        return this.maxItems;
    }

    public Boolean getUniqueItems() {
        return this.uniqueItems;
    }

    public boolean hasSingleSchemaForAllItems() {
        return items != null && items instanceof JsonSchema;
    }

    public JsonSchema getSingleSchemaForAllItems() {
        return hasSingleSchemaForAllItems() ? (JsonSchema) items : null;
    }

    public void setSingleSchemaForAllItems(JsonSchema value){
        this.items = value;
    }

    public boolean hasSchemaArrayForItems() {
        return items != null && items.getClass().isArray();
    }

    public JsonSchema[] getSchemaArrayForItems() {
        return hasSchemaArrayForItems() ? (JsonSchema[]) items : null;
    }

    public void setSchemaArrayForItems(JsonSchema[] value){
        this.items = value;
    }

    public boolean hasSchemaForAdditionalItems(){
        return additionalItems!=null;
    }

    public JsonSchema getSchemaForAdditionalItems(){
        return additionalItems;
    }

    public void setMaxItems(Integer maxItems) {
        this.maxItems = maxItems;
    }

    public void setUniqueItems(Boolean uniqueItems) {
        this.uniqueItems = uniqueItems;
    }

    @Override
    public boolean isArraySchema() {
        return true;
    }

    @Override
    public JsonArraySchema asArraySchema() {
        return this;
    }

    public void setAdditionalItems(JsonSchema additionalItems) {
        this.additionalItems = additionalItems;
    }
}

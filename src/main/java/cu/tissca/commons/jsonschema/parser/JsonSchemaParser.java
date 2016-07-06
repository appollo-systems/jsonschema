package cu.tissca.commons.jsonschema.parser;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import cu.tissca.commons.jsonschema.model.*;
import elemental.json.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author ariel.viera@gmail.com (Ariel Viera)
 */
public class JsonSchemaParser {

    private enum JsonSchemaType {ARRAY, BOOLEAN, ENUM, INTEGER, NULL, NUMBER, OBJECT, STRING, UNKNOWN}

    ;
    private Map<String, JsonSchemaType> TYPE_MAP = ImmutableMap.<String, JsonSchemaType>builder()
            .put("array", JsonSchemaType.ARRAY)
            .put("boolean", JsonSchemaType.BOOLEAN)
            .put("integer", JsonSchemaType.INTEGER)
            .put("null", JsonSchemaType.NULL)
            .put("number", JsonSchemaType.NUMBER)
            .put("object", JsonSchemaType.OBJECT)
            .put("string", JsonSchemaType.STRING)
            .build();

    private TypeReader typeReader = new TypeReader();


    public JsonSchema parse(String text) throws JsonSchemaParserException {
        try {
            JsonObject json = Json.parse(text);
            return parseJsonSchema(json);
        } catch (JsonException exception){
            throw new JsonSchemaParserException("Error in JSON", exception);
        }
    }

    private JsonSchema parseJsonSchema(JsonObject json) throws JsonSchemaParserException {
        JsonSchemaType jsonSchemaType = identifyType(json);
        switch (jsonSchemaType) {
            case ARRAY:
                return parseArray(json);
            case BOOLEAN:
                return parseBoolean(json);
            case ENUM:
                return parseEnum(json);
            case INTEGER:
                return parseInteger(json);
            case NULL:
                return parseNull(json);
            case NUMBER:
                return parseNumber(json);
            case OBJECT:
                return parseObject(json);
            case STRING:
                return parseString(json);
            case UNKNOWN:
            default:
                throw new JsonSchemaParserException("Could not identify schema type in json \"" + json + "\"");
        }
    }

    private JsonSchema parseBoolean(JsonObject json) {
        return new JsonBooleanSchema();
    }

    @VisibleForTesting
    JsonStringSchema parseString(JsonObject json) {
        JsonStringSchema result = new JsonStringSchema();
        typeReader.readJsonSchema(json, result);
        typeReader.readString(json, result);
        return result;
    }

    @VisibleForTesting
    JsonNumberSchema parseNumber(JsonObject json) {
        JsonNumberSchema result = new JsonNumberSchema();
        typeReader.readJsonSchema(json, result);
        typeReader.readNumberSchema(json, result);
        return result;
    }

    @VisibleForTesting
    JsonObjectSchema parseObject(JsonObject json) throws JsonSchemaParserException {
        JsonObjectSchema result = new JsonObjectSchema();
        typeReader.readJsonSchema(json, result);
        typeReader.readObjectSchema(json, result);
        return result;
    }

    @VisibleForTesting
    JsonNullSchema parseNull(JsonObject json) {
        return new JsonNullSchema();
    }

    @VisibleForTesting
    JsonIntegerSchema parseInteger(JsonObject json) {
        JsonIntegerSchema result = new JsonIntegerSchema();
        typeReader.readJsonSchema(json, result);
        typeReader.readNumberSchema(json, result);
        typeReader.readIntegerSchema(json, result);
        return result;
    }

    @VisibleForTesting
    JsonEnumSchema parseEnum(JsonObject json) throws JsonSchemaParserException {
        JsonEnumSchema result = new JsonEnumSchema();
        typeReader.readJsonSchema(json, result);
        typeReader.readEnumSchema(json, result);
        return result;
    }

    @VisibleForTesting
    JsonArraySchema parseArray(JsonObject json) throws JsonSchemaParserException {
        JsonArraySchema result = new JsonArraySchema();
        typeReader.readJsonSchema(json, result);
        typeReader.readArraySchema(json, result);
        return result;
    }

    @VisibleForTesting
    JsonSchemaType identifyType(JsonObject json) {
        if (json.hasKey("enum"))
            return JsonSchemaType.ENUM;
        if (!json.hasKey("type"))
            return JsonSchemaType.UNKNOWN;
        String type = json.getString("type").toLowerCase();
        JsonSchemaType jsonSchemaType = TYPE_MAP.get(type);
        if (jsonSchemaType == null) jsonSchemaType = JsonSchemaType.UNKNOWN;
        return jsonSchemaType;
    }

    /**
     * @author ariel.viera@gmail.com (Ariel Viera)
     */
    public class TypeReader {
        public void readString(JsonObject json, JsonStringSchema result) {
            if (json.hasKey("maxLength")) {
                result.setMaxLength((int) json.getNumber("maxLength"));
            }
            if (json.hasKey("minLength")) {
                result.setMinLength((int) json.getNumber("minLength"));
            }
            if (json.hasKey("pattern")) {
                result.setPattern(json.getString("pattern"));
            }
        }

        public void readJsonSchema(JsonObject json, JsonSchema result) {
            if(json.hasKey("default")) {
                result.setDefault(json.getString("default"));
            }
            if(json.hasKey("description")) {
                result.setDescription(json.getString("description"));
            }
        }

        void readNumberSchema(JsonObject json, JsonNumberSchema result) {
            if (json.hasKey("exclusiveMaximum")) {
                result.setExclusiveMaximum(json.getBoolean("exclusiveMaximum"));
            }
            if (json.hasKey("exclusiveMinimum")) {
                result.setExclusiveMinimum(json.getBoolean("exclusiveMinimum"));
            }
            if (json.hasKey("maximum")) {
                result.setMaximum((int) json.getNumber("maximum"));
            }
            if (json.hasKey("minimum")) {
                result.setMinimum((int) json.getNumber("minimum"));
            }
        }

        public void readObjectSchema(JsonObject json, JsonObjectSchema result) throws JsonSchemaParserException {
            if(json.hasKey("properties")) {
                JsonObject jsonprops = json.getObject("properties");
                String[] keys = jsonprops.keys();
                Map<String, JsonSchema> properties = new HashMap<>();
                for (String key : keys) {
                    JsonValue jsonValue = jsonprops.get(key);
                    if (jsonValue.getType() != JsonType.OBJECT) {
                        throw new JsonSchemaParserException("Error while reading property. Expected OBJECT but " + jsonValue.getType() + " found.");
                    }
                    assert jsonValue instanceof JsonObject;
                    JsonSchema value = JsonSchemaParser.this.parseJsonSchema((JsonObject) jsonValue);
                    properties.put(key, value);
                }
                result.setProperties(properties);
            }
            if(json.hasKey("required")){
                JsonArray jsonrequired = json.getArray("required");
                Set<String> required = new HashSet<>();
                for(int i=0;i<jsonrequired.length();i++){
                    required.add(jsonrequired.getString(i));
                }
                result.setRequired(required);
            }
        }

        public void readIntegerSchema(JsonObject json, JsonIntegerSchema result) {
            if(json.hasKey("multipleOf")){
                result.setMultipleOf((int) json.getNumber("multipleOf"));
            }
        }

        public void readEnumSchema(JsonObject json, JsonEnumSchema result) throws JsonSchemaParserException {
            JsonArray anEnum = json.getArray("enum");
            Set<String> enumKeys = new HashSet<>();
            for(int i=0;i<anEnum.length();i++){
                JsonValue jsonValue = anEnum.get(i);
                if(!(jsonValue instanceof JsonString))
                    throw new JsonSchemaParserException("Error reading enum key. Expected STRING but "+jsonValue.getType()+ " found.");
                enumKeys.add(((JsonString) jsonValue).getString());
            }
            result.setEnum(enumKeys);
        }

        public void readArraySchema(JsonObject json, JsonArraySchema result) throws JsonSchemaParserException {
            if(json.hasKey("maxItems")){
                result.setMaxItems((int) json.getNumber("maxItems"));
            }
            if(json.hasKey("minItems")){
                result.setMinItems((int) json.getNumber("minItems"));
            }
            if(json.hasKey("uniqueItems")){
                result.setUniqueItems(json.getBoolean("uniqueItems"));
            }
            if(json.hasKey("items")){
                JsonValue items = json.get("items");
                switch(items.getType()) {
                    case OBJECT:
                        result.setSingleSchemaForAllItems(JsonSchemaParser.this.parseJsonSchema((JsonObject) items));
                        break;
                    case ARRAY:
                        JsonArray jsonArray = (JsonArray) items;
                        JsonSchema[] schemas = new JsonSchema[jsonArray.length()];
                        for(int i=0;i<schemas.length;i++){
                            JsonValue jsonValue = jsonArray.get(i);
                            if(jsonValue.getType()!= JsonType.OBJECT){
                                throw new JsonSchemaParserException("Error while reading array schema items property. Expected SCHEMA object in item but found "+jsonValue.getType());
                            }
                            schemas[i] = JsonSchemaParser.this.parseJsonSchema((JsonObject) jsonValue);
                        }
                        result.setSchemaArrayForItems(schemas);
                        break;
                    default:
                        throw new JsonSchemaParserException("Error while reading array schema items property. Expected OBJECT or ARRAY but "+items.getType()+ " found.");
                }
            }
        }
    }
}

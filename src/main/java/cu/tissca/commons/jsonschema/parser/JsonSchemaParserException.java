package cu.tissca.commons.jsonschema.parser;

import elemental.json.JsonException;

/**
 * @author ariel.viera@gmail.com (Ariel Viera)
 */
public class JsonSchemaParserException extends Throwable {
    public JsonSchemaParserException(String s) {
        super(s);
    }

    public JsonSchemaParserException(String s, JsonException exception) {
        super(s, exception);
    }
}

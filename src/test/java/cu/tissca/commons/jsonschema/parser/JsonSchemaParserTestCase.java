package cu.tissca.commons.jsonschema.parser;

import cu.tissca.commons.jsonschema.model.*;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertThat;

/**
 * Created by avd on 2016-05-28.
 */
public class JsonSchemaParserTestCase {

    public static final String SAMPLE_STRING_SCHEMA = "{\n" +
            "    \"type\": \"string\",\n" +
            "    \"maxLength\": 10,\n" +
            "    \"minLength\": 1, \n" +
            "    \"pattern\": \"sample-pattern\"\n" +
            "}";
    public static final String SAMPLE_ENUM_SCHEMA = "{\n" +
            "    \"enum\": [ \"array\", \"boolean\", \"integer\", \"null\", \"number\", \"object\", \"string\" ]\n" +
            "}";

    public static final String SAMPLE_NUMBER_SCHEMA = "{\n" +
            "    \"description\": \"Age in years\",\n" +
            "    \"type\": \"number\",\n" +
            "    \"minimum\": 1,\n" +
            "    \"maximum\": 10,\n" +
            "    \"exclusiveMinimum\": true,\n" +
            "    \"exclusiveMaximum\": true\n" +
            "}";

    public static final String SAMPLE_INTEGER_SCHEMA = "{\n" +
            "    \"description\": \"Age in years\",\n" +
            "    \"type\": \"integer\",\n" +
            "    \"minimum\": 1,\n" +
            "    \"maximum\": 10,\n" +
            "    \"exclusiveMinimum\": true,\n" +
            "    \"exclusiveMaximum\": true,\n" +
            "    \"multipleOf\": 2\n" +
            "}";
    public static final String SAMPLE_ARRAY_SCHEMA = "{\n" +
            "    \"type\": \"array\",\n" +
            "    \"items\": { \"type\": \"string\" },\n" +
            "    \"minItems\": 1,\n" +
            "    \"maxItems\": 10,\n" +
            "    \"uniqueItems\": true\n" +
            "}";
    private static final String SAMPLE_BOOLEAN_SCHEMA = "{\n" +
            "    \"type\": \"boolean\",\n" +
            "    \"default\": \"false\"\n" +
            "}";
    public static final String SAMPLE_OBJECT_SCHEMA = "{\n" +
            "    \"title\": \"Example Schema with three properties\",\n" +
            "    \"description\": \"Example Schema with three properties\",\n" +
            "    \"type\": \"object\",\n" +
            "    \"properties\": {\n" +
            "        \"firstName\": {\n" +
            "            \"type\": \"string\"\n" +
            "        },\n" +
            "        \"lastName\": {\n" +
            "            \"type\": \"string\"\n" +
            "        },\n" +
            "        \"age\": {\n" +
            "            \"description\": \"Age in years\",\n" +
            "            \"type\": \"integer\",\n" +
            "            \"minimum\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    \"required\": [\n" +
            "        \"firstName\",\n" +
            "        \"lastName\"\n" +
            "    ]\n" +
            "}";
    private static final String SAMPLE_NULL_SCHEMA = "{\"type\": \"null\"}";

    private JsonSchemaParser jsonSchemaParser;

    @Before
    public void before(){
        this.jsonSchemaParser = new JsonSchemaParser();
    }

    @Test
    public void testEnumSchemaIsIdentifiedCorrectly() throws JsonSchemaParserException {
        JsonSchema schema = jsonSchemaParser.parse(SAMPLE_ENUM_SCHEMA);
        Assert.assertTrue(schema.isEnumSchema());
    }

    @Test
    public void testEnumSchemaIsReadCorrectly() throws JsonSchemaParserException {
        JsonSchema schema = jsonSchemaParser.parse(SAMPLE_ENUM_SCHEMA);
        Assume.assumeTrue(schema.isEnumSchema());
        JsonEnumSchema jsonEnumSchema = schema.asEnumSchema();
        assertThat(jsonEnumSchema.getEnum(), CoreMatchers.hasItems("array", "boolean", "integer", "null", "number", "object", "string"));
    }

    @Test
    public void testArraySchemaIsIdentifiedCorrectly() throws JsonSchemaParserException {
        JsonSchema schema = jsonSchemaParser.parse(SAMPLE_ARRAY_SCHEMA);
        Assert.assertTrue(schema.isArraySchema());
    }

    @Test
    public void testArraySchemaIsReadCorrectly() throws JsonSchemaParserException {
        JsonSchema schema = jsonSchemaParser.parse(SAMPLE_ARRAY_SCHEMA);
        Assume.assumeTrue(schema.isArraySchema());
        JsonArraySchema jsonArraySchema = schema.asArraySchema();
        assertThat(jsonArraySchema.getMinItems(), CoreMatchers.is(1));
        assertThat(jsonArraySchema.getMaxItems(), CoreMatchers.is(10));
        assertThat(jsonArraySchema.getUniqueItems(), CoreMatchers.is(true));
        Assert.assertFalse(jsonArraySchema.hasSchemaArrayForItems());
        assertThat(jsonArraySchema.getSchemaArrayForItems(), CoreMatchers.is(CoreMatchers.nullValue()));
        Assert.assertFalse(jsonArraySchema.hasSchemaForAdditionalItems());
        assertThat(jsonArraySchema.getSchemaForAdditionalItems(), CoreMatchers.is(CoreMatchers.nullValue()));

        Assert.assertTrue(jsonArraySchema.hasSingleSchemaForAllItems());
        assertThat(jsonArraySchema.getSingleSchemaForAllItems(), CoreMatchers.is(CoreMatchers.notNullValue()));

        JsonSchema singleSchemaForAllItems = jsonArraySchema.getSingleSchemaForAllItems();
        Assert.assertTrue(singleSchemaForAllItems.isStringSchema());
    }

    @Test
    public void testBooleanSchemaIsIdentifiedCorrectly() throws JsonSchemaParserException {
        JsonSchema jsonSchema = jsonSchemaParser.parse(SAMPLE_BOOLEAN_SCHEMA);
        Assert.assertTrue(jsonSchema.isBooleanSchema());
    }

    @Test
    public void testIntegerSchemaIsIdentifiedCorrectly() throws JsonSchemaParserException {
        JsonSchema schema = jsonSchemaParser.parse(SAMPLE_INTEGER_SCHEMA);
        Assert.assertTrue(schema.isIntegerSchema());
    }

    @Test
    public void testIntegerSchemaIsAlsoNumberSchema() throws JsonSchemaParserException {
        JsonSchema schema = jsonSchemaParser.parse(SAMPLE_INTEGER_SCHEMA);
        Assume.assumeTrue(schema.isIntegerSchema());
        Assert.assertTrue(schema.isNumberSchema());
    }

    @Test
    public void testNullSchemaIsIdentifiedCorrectly() throws JsonSchemaParserException {
        JsonSchema schema = jsonSchemaParser.parse(SAMPLE_NULL_SCHEMA);
        Assert.assertTrue(schema.isNullSchema());
    }

    @Test
    public void testIntegerSchemaIsReadCorrectly() throws JsonSchemaParserException {
        JsonSchema schema = jsonSchemaParser.parse(SAMPLE_INTEGER_SCHEMA);
        Assume.assumeTrue(schema.isIntegerSchema());
        JsonIntegerSchema jsonIntegerSchema = schema.asIntegerSchema();
        assertThat(jsonIntegerSchema.getDescription(), CoreMatchers.is("Age in years"));
        assertThat(jsonIntegerSchema.getMaximum(), CoreMatchers.is(10));
        assertThat(jsonIntegerSchema.getMinimum(), CoreMatchers.is(1));
        assertThat(jsonIntegerSchema.getExclusiveMaximum(), CoreMatchers.is(true));
        assertThat(jsonIntegerSchema.getExclusiveMinimum(), CoreMatchers.is(true));
        assertThat(jsonIntegerSchema.getMultipleOf(), CoreMatchers.is(2));
    }

    @Test
    public void testNumberSchemaIsIdentifiedCorrectly() throws JsonSchemaParserException {
        JsonSchema schema = jsonSchemaParser.parse(SAMPLE_NUMBER_SCHEMA);
        Assert.assertTrue(schema.isNumberSchema());
    }

    @Test
    public void testNumberSchemaIsReadCorrectly() throws JsonSchemaParserException {
        JsonSchema schema = jsonSchemaParser.parse(SAMPLE_NUMBER_SCHEMA);
        Assume.assumeTrue(schema.isNumberSchema());
        JsonNumberSchema jsonIntegerSchema = schema.asNumberSchema();
        assertThat(jsonIntegerSchema.getDescription(), CoreMatchers.is("Age in years"));
        assertThat(jsonIntegerSchema.getMaximum(), CoreMatchers.is(10));
        assertThat(jsonIntegerSchema.getMinimum(), CoreMatchers.is(1));
        assertThat(jsonIntegerSchema.getExclusiveMaximum(), CoreMatchers.is(true));
        assertThat(jsonIntegerSchema.getExclusiveMinimum(), CoreMatchers.is(true));
    }

    @Test
    public void testObjectSchemaIsIdentifiedCorrectly() throws JsonSchemaParserException {
        JsonSchema schema = jsonSchemaParser.parse(SAMPLE_OBJECT_SCHEMA);
        assertThat(schema.isObjectSchema(), CoreMatchers.is(true));
    }

    @Test
    public void testObjectSchemaIsReadCorrectly() throws JsonSchemaParserException {
        JsonSchema jsonSchema = jsonSchemaParser.parse(SAMPLE_OBJECT_SCHEMA);
        Assume.assumeThat(jsonSchema.isObjectSchema(), CoreMatchers.is(true));
        JsonObjectSchema jsonObjectSchema = jsonSchema.asObjectSchema();
        Map<String, JsonSchema> propertySchemas = jsonObjectSchema.getProperties();
        assertThat(propertySchemas.get("firstName"), CoreMatchers.is(CoreMatchers.instanceOf(JsonStringSchema.class)));
        assertThat(propertySchemas.get("lastName"), CoreMatchers.is(CoreMatchers.instanceOf(JsonStringSchema.class)));
        assertThat(propertySchemas.get("age"), CoreMatchers.is(CoreMatchers.instanceOf(JsonIntegerSchema.class)));
        assertThat(jsonObjectSchema.getRequired(), CoreMatchers.hasItems("firstName", "lastName"));
    }

    @Test
    public void testStringSchemaIsIdentifiedCorrectly() throws JsonSchemaParserException {
        JsonSchema schema = jsonSchemaParser.parse(SAMPLE_STRING_SCHEMA);
        Assert.assertTrue(schema.isStringSchema());
    }

    @Test
    public void testStringSchemaIsReadCorrectly() throws JsonSchemaParserException {
        JsonSchema schema = jsonSchemaParser.parse(SAMPLE_STRING_SCHEMA);
        Assume.assumeTrue(schema.isStringSchema());
        JsonStringSchema stringSchema = schema.asStringSchema();
        assertThat(stringSchema.getMaxLength(), CoreMatchers.is(10));
        assertThat(stringSchema.getMinLength(), CoreMatchers.is(1));
        assertThat(stringSchema.getPattern(), CoreMatchers.is("sample-pattern"));
    }
}
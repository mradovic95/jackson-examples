package com.raf.examples.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raf.examples.model.ChildOne;
import com.raf.examples.model.ChildTwo;
import com.raf.examples.model.ImmutableProduct;
import com.raf.examples.model.Parent;
import com.raf.examples.model.Product;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JacksonJsonTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testPojoSerialization() throws JsonProcessingException {
        //GIVEN
        Product product = new Product("product title", "product description", BigDecimal.TEN);
        //WHEN
        String productAsString = objectMapper.writeValueAsString(product);
        //THEN
        assertEquals("{\"title\":\"product title\",\"description\":\"product description\",\"price\":10}",
                productAsString);
    }

    @Test
    public void testPojoDeserialization() throws JsonProcessingException {
        //GIVEN
        String productAsString = "{\"title\":\"product title\",\"description\":\"product description\",\"price\":10}";
        //WHEN
        Product product = objectMapper.readValue(productAsString, Product.class);
        JsonNode jsonNode = objectMapper.readTree(productAsString);
        //THEN
        assertEquals("product title", product.getTitle());
        assertEquals("product description", product.getDescription());
        assertEquals(BigDecimal.TEN, product.getPrice());
    }

    @Test
    public void testImmutablePojoSerialization() throws JsonProcessingException {
        //GIVEN
        ImmutableProduct immutableProduct = new ImmutableProduct("product title", "product description",
                BigDecimal.TEN);
        //WHEN
        String productAsString = objectMapper.writeValueAsString(immutableProduct);
        //THEN
        assertEquals("{\"title\":\"product title\",\"description\":\"product description\",\"price\":10}",
                productAsString);
    }

    @Test
    public void testImmutablePojoDeserialization() throws JsonProcessingException {
        //GIVEN
        String productAsString = "{\"title\":\"product title\",\"description\":\"product description\",\"price\":10}";
        //WHEN
        ImmutableProduct immutableProduct = objectMapper.readValue(productAsString, ImmutableProduct.class);
        //THEN
        assertEquals("product title", immutableProduct.getTitle());
        assertEquals("product description", immutableProduct.getDescription());
        assertEquals(BigDecimal.TEN, immutableProduct.getPrice());
    }

    @Test
    public void testPojoSerializationToFile() throws IOException, URISyntaxException {
        //GIVEN
        Product product = new Product("product title", "product description", BigDecimal.TEN);
        File file = new File(getClass().getClassLoader().getResource("testPojoSerializationToFile.json").toURI());
        //WHEN
        objectMapper.writeValue(file, product);
        //THEN
        String fileContent = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                .getResource("testPojoSerializationToFile.json").getPath())));
        assertEquals("{\"title\":\"product title\",\"description\":\"product description\",\"price\":10}",
                fileContent);
    }

    @Test
    public void testLoadPojoFromFile() throws IOException, URISyntaxException {
        //GIVEN
        File file = new File(getClass().getClassLoader().getResource("testLoadPojoFromFile.json").toURI());
        //WHEN
        Product product = objectMapper.readValue(file, Product.class);
        //THEN
        assertEquals("product title", product.getTitle());
        assertEquals("product description", product.getDescription());
        assertEquals(BigDecimal.TEN, product.getPrice());
    }

    @Test
    public void testPojoSerializationHierarchy() throws JsonProcessingException {
        //GIVEN
        ChildOne childOne = new ChildOne("name", "description", "childOneProperty");
        ChildTwo childTwo = new ChildTwo("name", "description", "childTwoProperty");
        //WHEN
        String childOneAsString = objectMapper.writeValueAsString(childOne);
        String childTwoAsString = objectMapper.writeValueAsString(childTwo);
        //THEN
        assertEquals("{\"type\":\"childOne\",\"name\":\"name\",\"description\":\"description\",\"childOneProperty\":\"childOneProperty\"}",
                childOneAsString);
        assertEquals("{\"type\":\"childTwo\",\"name\":\"name\",\"description\":\"description\",\"childTwoProperty\":\"childTwoProperty\"}",
                childTwoAsString);
    }

    @Test
    public void testPojoDeserializationHierarchy() throws JsonProcessingException {
        //GIVEN
        String content = "[{\"type\":\"childOne\",\"name\":\"name\",\"description\":\"description\",\"childOneProperty\":\"childOneProperty\"},"
                + "{\"type\":\"childTwo\",\"name\":\"name\",\"description\":\"description\",\"childTwoProperty\":\"childTwoProperty\"}]";
        //WHEN
        List<Parent> parents = objectMapper.readValue(content, new TypeReference<List<Parent>>() {
        });
        //THEN
        assertEquals(2, parents.size());
        assertEquals(parents.get(0).getClass(), ChildOne.class);
        assertEquals(parents.get(0).getName(), "name");
        assertEquals(parents.get(0).getDescription(), "description");
        assertEquals(((ChildOne) parents.get(0)).getChildOneProperty(), "childOneProperty");
        assertEquals(parents.get(1).getClass(), ChildTwo.class);
        assertEquals(parents.get(1).getName(), "name");
        assertEquals(parents.get(1).getDescription(), "description");
        assertEquals(((ChildTwo) parents.get(1)).getChildTwoProperty(), "childTwoProperty");
    }

    @Test
    public void testCreateJsonNodeFromJson() throws JsonProcessingException {
        //GIVEN
        String content = "{\n" +
                "   \"firstName\":\"Pera\",\n" +
                "   \"lastName\":\"Peric\",\n" +
                "   \"age\":20,\n" +
                "   \"address\":{\n" +
                "      \"line1\":\"Apt. 123\",\n" +
                "      \"line2\":\"321 Main Street\",\n" +
                "      \"city\":\"New York\"\n" +
                "   }\n" +
                "}";
        //WHEN
        JsonNode person = objectMapper.readTree(content);
        //THEN
        assertEquals("Pera", person.get("firstName").asText());
        assertEquals("Peric", person.get("lastName").asText());
        assertEquals(20, person.get("age").asInt());
        JsonNode address = person.get("address");
        assertEquals("Apt. 123", address.get("line1").asText());
        assertEquals("321 Main Street", address.get("line2").asText());
        assertEquals("New York", address.get("city").asText());
    }
}

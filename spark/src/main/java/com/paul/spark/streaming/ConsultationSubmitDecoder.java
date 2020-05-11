package com.paul.spark.streaming;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paul.spark.model.ConsultationSubmit;
import kafka.serializer.Decoder;
import kafka.utils.VerifiableProperties;

import java.io.IOException;
import java.nio.charset.Charset;

public class ConsultationSubmitDecoder implements Decoder<ConsultationSubmit> {
    private final Charset encoding;

    @Override
    public ConsultationSubmit fromBytes(byte[] bytes) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(new String(bytes, encoding), ConsultationSubmit.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ConsultationSubmitDecoder(VerifiableProperties props) {
        this.encoding = props == null ? UTF_8 : Charset.forName(props.getString("serializer.encoding", UTF_8.name()));
    }
}

package com.lyy.eslearn.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author YueYang
 * Created on 2025/11/3 19:37
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Attr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field(name = "brand", type = FieldType.Keyword)
    @JsonProperty("brand")
    private String brand;

    @Field(name = "category", type = FieldType.Keyword)
    @JsonProperty("category")
    private String category;
}

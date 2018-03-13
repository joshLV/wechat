package com.bingosoft.models.entities;

import lombok.Data;

@Data
public class EsbRoot<T> {
       private EsbHeaderBody HEADER;
       private T BODY;
}


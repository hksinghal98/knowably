package com.knowably.notificationservice.domain;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryResponse {
    String query;
    String domain;
    String[] result;
    LocalDateTime localDateTime;

}


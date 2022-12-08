package com.kodlamaio.common.events.filterService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandUpdatedEvent {
    private String id;
    private String name;
}

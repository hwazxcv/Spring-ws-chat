package com.stomp.chat.chat;

import com.stomp.chat.common.utils.Pagination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListData<T> {
    private List<T> content;
    private Pagination pagination;

}

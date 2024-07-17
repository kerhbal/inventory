package domainapp.modules.simple.external;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Record<T> extends RecordToCreate<T>{
    String id;
    LocalDateTime createdTime;
}

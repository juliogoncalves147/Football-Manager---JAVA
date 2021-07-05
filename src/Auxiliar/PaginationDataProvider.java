package Auxiliar;

import java.io.Serializable;
import java.util.List;

public interface PaginationDataProvider<T> extends Serializable {
    int getTotalRowCount();
    List<T> getRows(int startIndex, int endIndex);
}
package eu.execom.core.dto.base;

/**
 * Marker interface to be implemented by all search objects.
 * 
 * @author Dusko Vesin
 */
public interface SearchObject {

    /**
     * Get index of first result that needs to be shown.
     * 
     * @return - first index
     */
    int getFrom();

    /**
     * Set index of first result that needs to be shown.
     * 
     * @param from
     *            - first index
     */
    void setFrom(int from);

    /**
     * Get maximum number of records that needs to be returned.
     * 
     * @return - maximum number of rows
     */
    int getMaxRowCount();

    /**
     * Set maximum number of records that needs to be returned.
     * 
     * @param maxRowCount
     *            - maximum number of rows
     */
    void setMaxRowCount(int maxRowCount);

    /**
     * Get specified search order.
     * 
     * @return - search order
     */
    SearchOrder getSearchOrder();

    /**
     * Set desired search order.
     * 
     * @param searchOrder
     *            - search order
     */
    void setSearchOrder(SearchOrder searchOrder);

    /**
     * Get column by which results are going to be sorted.
     * 
     * @return - sort by column
     */
    String getSortColumn();

    /**
     * Set column by which results are going to be sorted.
     * 
     * @param sortColumn
     *            - sort by column
     */
    void setSortColumn(String sortColumn);

}

package eu.execom.core.dto.base;

import eu.execom.core.dto.AbstractDto;

/**
 * Base class for all search objects.
 * 
 * @author Dusko Vesin
 */
public abstract class AbstractSearchDto extends AbstractDto implements SearchObject {

    private int from;
    private int maxRowCount;
    private SearchOrder searchOrder;
    private String sortColumn;

    /**
     * Default constructor.
     */
    public AbstractSearchDto() {
        super();
    }

    /**
     * Parameterized constructor.
     * 
     * @param from
     *            for which number.
     * @param maxRowCount
     *            max rows that can occurs in result
     * @param searchOrder
     *            search order.
     * @param sortColumn
     *            sort column.
     */
    public AbstractSearchDto(final int from, final int maxRowCount, final SearchOrder searchOrder,
            final String sortColumn) {
        this();
        this.searchOrder = searchOrder;
        this.from = from;
        this.maxRowCount = maxRowCount;
        this.sortColumn = sortColumn;
    }

    @Override
    public int getFrom() {
        return from;
    }

    @Override
    public void setFrom(final int from) {
        this.from = from;
    }

    @Override
    public int getMaxRowCount() {
        return maxRowCount;
    }

    @Override
    public void setMaxRowCount(final int maxRowCount) {
        this.maxRowCount = maxRowCount;
    }

    @Override
    public SearchOrder getSearchOrder() {
        return searchOrder;
    }

    @Override
    public void setSearchOrder(final SearchOrder searchOrder) {
        this.searchOrder = searchOrder;
    }

    @Override
    public void setSortColumn(final String sortColumn) {
        this.sortColumn = sortColumn;
    }

    @Override
    public String getSortColumn() {
        return sortColumn;
    }

}

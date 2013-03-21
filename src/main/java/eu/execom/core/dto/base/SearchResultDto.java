package eu.execom.core.dto.base;

import java.util.List;

import eu.execom.core.dto.AbstractDto;

/**
 * Integrate result list {@link List} and total count.
 * 
 * @author Dusko Vesin
 * @param <T>
 */
public class SearchResultDto<T> extends AbstractDto {

    private final List<T> results;
    private final Long totalCount;

    /**
     * Default constructor.
     * 
     * @param results
     *            list of results.
     * @param totalCount
     *            total count results.
     */
    public SearchResultDto(final List<T> results, final Long totalCount) {
        super();
        this.results = results;
        this.totalCount = totalCount;
    }

    /**
     * Get results.
     * 
     * @return {@link List} of results.
     */
    public List<T> getResults() {
        return results;
    }

    /**
     * Total result count.
     * 
     * @return result count.
     */
    public Long getTotalCount() {
        return totalCount;
    }

}

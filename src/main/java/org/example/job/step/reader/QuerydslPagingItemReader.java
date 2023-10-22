package org.example.job.step.reader;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

/**
 * QueryDsl Paging ItemReader.
 *
 * @see <a href="https://woowabros.github.io/experience/2020/02/05/springbatch-querydsl.html"
 *     target="_blank">springbatch-querydsl</a>
 */
public class QuerydslPagingItemReader<T> extends AbstractPagingItemReader<T> {

  protected final Map<String, Object> jpaPropertyMap = new HashMap<>();

  protected EntityManagerFactory entityManagerFactory;

  protected EntityManager entityManager;

  protected Function<JPAQueryFactory, JPAQuery<T>> queryFunction;

  protected boolean transacted = true;

  protected QuerydslPagingItemReader() {
    setName(ClassUtils.getShortName(QuerydslPagingItemReader.class));
  }

  public QuerydslPagingItemReader(
      final EntityManagerFactory entityManagerFactory,
      final int pageSize,
      final Function<JPAQueryFactory, JPAQuery<T>> queryFunction) {
    this();
    this.entityManagerFactory = entityManagerFactory;
    this.queryFunction = queryFunction;
    setPageSize(pageSize);
  }

  @SuppressWarnings("unused")
  public void setTransacted(final boolean transacted) {
    this.transacted = transacted;
  }

  @Override
  protected void doOpen() throws Exception {
    super.doOpen();

    entityManager = entityManagerFactory.createEntityManager(jpaPropertyMap);
    if (entityManager == null) {
      throw new DataAccessResourceFailureException("Unable to obtain an EntityManager");
    }
  }

  @Override
  protected void doReadPage() {
    clearIfTransacted();

    JPAQuery<T> query = createQuery().offset((long) getPage() * getPageSize()).limit(getPageSize());

    initResults();

    fetchQuery(query);
  }

  protected void clearIfTransacted() {
    if (transacted) {
      entityManager.clear();
    }
  }

  protected JPAQuery<T> createQuery() {
    JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
    return queryFunction.apply(queryFactory);
  }

  protected void initResults() {
    if (CollectionUtils.isEmpty(super.results)) {
      super.results = new CopyOnWriteArrayList<>();
    } else {
      super.results.clear();
    }
  }

  protected void fetchQuery(final JPAQuery<T> query) {
    if (!transacted) {
      List<T> queryResult = query.fetch();
      for (T entity : queryResult) {
        entityManager.detach(entity);
        super.results.add(entity);
      }
    } else {
      super.results.addAll(query.fetch());
    }
  }

  @Override
  protected void doClose() throws Exception {
    entityManager.close();
    super.doClose();
  }
}

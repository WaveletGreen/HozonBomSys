package integration.service.i;

import javax.xml.ws.Holder;

/**
 * 进行RFC调用
 */
public interface IExecutor  {
    <T, E> void doExecute(Holder<T> input, Holder<E> output) throws Exception;
}

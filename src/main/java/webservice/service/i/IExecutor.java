package webservice.service.i;

import javax.xml.ws.Holder;

public interface IExecutor {
    <T, E> void doExecute(Holder<T> input, Holder<E> output);
}

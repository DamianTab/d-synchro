package damian.tab.core.config;

import damian.tab.core.thread.ClientListenerRunnable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * Bean PostProcessor that handles destruction of prototype beans
 */
@Component
public class DestroyPrototypeBeansPostProcessor implements BeanPostProcessor, BeanFactoryAware, DisposableBean {

    private BeanFactory beanFactory;

    private final List<Object> prototypeBeans = new LinkedList<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanFactory.isPrototype(beanName) && !(bean instanceof ClientListenerRunnable)) {
            synchronized (prototypeBeans) {
                prototypeBeans.add(bean);
            }
        }
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void destroy() throws Exception {
        synchronized (prototypeBeans) {
            for (Object bean : prototypeBeans) {
                if (bean instanceof DisposableBean) {
                    DisposableBean disposable = (DisposableBean) bean;
                    disposable.destroy();
                } else if (bean instanceof AutoCloseable) {
                    AutoCloseable autoCloseable = (AutoCloseable) bean;
                    autoCloseable.close();
                }
            }
            prototypeBeans.clear();
        }
    }
}


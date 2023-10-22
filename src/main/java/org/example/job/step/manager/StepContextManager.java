package org.example.job.step.manager;

import java.util.Objects;
import java.util.function.Function;
import lombok.experimental.UtilityClass;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;

@UtilityClass
public class StepContextManager {

    public static <T> T get(final String name, final Function<Object, T> mapper){
        StepContext stepContext = Objects.requireNonNull(StepSynchronizationManager.getContext());

        return mapper.apply(stepContext.getStepExecutionContext().get(name));
    }
}

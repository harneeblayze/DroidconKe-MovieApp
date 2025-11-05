package com.gyg.codelab.movies.mvi2.state_manager

import org.koin.core.definition.Definition
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.ScopeDSL
import kotlin.reflect.KClass



inline fun <reified S : State, reified E : Event> ScopeDSL.scopedReducer(
  noinline definition: Definition<Reducer< S, E>>,
) {
  scoped(
    qualifier = buildQualifierString(S::class, E::class),
    definition = definition,
  )
}

internal fun <S : State, E : Event> Scope.getReducer(
  stateClass: KClass<S>,
  eventClass: KClass<out E>,
): Reducer< S, E>? {
  return getOrNull(
    Reducer::class,
    buildQualifierString(stateClass, eventClass),
  ) as? Reducer< S, E>
}

@PublishedApi
internal fun <S: State, E : Event> buildQualifierString(
  stateClass: KClass<S>,
  eventClass: KClass<E>,
) = named("reducer_S_${stateClass.qualifiedName}_E_${eventClass.qualifiedName}")

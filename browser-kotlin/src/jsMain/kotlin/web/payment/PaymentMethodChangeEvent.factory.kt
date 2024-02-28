// Automatically generated - do not modify!

@file:Suppress(
    "NOTHING_TO_INLINE",
)

package web.payment

import web.events.EventTarget
import web.events.EventType

inline fun PaymentMethodChangeEvent(
    type: EventType<PaymentMethodChangeEvent<EventTarget?>>,
): PaymentMethodChangeEvent<*> =
    PaymentMethodChangeEvent<EventTarget?>(
        type = type,
    )

inline fun PaymentMethodChangeEvent(
    type: EventType<PaymentMethodChangeEvent<EventTarget?>>,
    init: PaymentMethodChangeEventInit,
): PaymentMethodChangeEvent<*> =
    PaymentMethodChangeEvent<EventTarget?>(
        type = type,
        init = init,
    )

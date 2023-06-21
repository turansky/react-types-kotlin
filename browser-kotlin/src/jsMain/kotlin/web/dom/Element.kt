// Automatically generated - do not modify!

package web.dom

import js.core.ReadonlyArray
import js.core.Void
import web.aria.ARIAMixin
import web.cssom.StylePropertyMapReadOnly
import web.events.Event
import web.events.EventHandler
import web.fullscreen.FullscreenOptions
import web.geometry.DOMRect
import web.geometry.DOMRectList
import web.html.*
import web.mathml.MATHML_NAMESPACE
import web.mathml.MathMLElement
import web.mathml.MathMLTagName
import web.scroll.ScrollIntoViewOptions
import web.scroll.ScrollToOptions
import web.svg.SVGElement
import web.svg.SVG_NAMESPACE
import web.svg.SvgTagName
import kotlin.js.Promise

abstract external class Element :
    Node,
    ARIAMixin,
    /* Animatable, */
    ChildNode,
    InnerHTML,
    NonDocumentTypeChildNode,
    ParentNode,
    Slottable {
    val attributes: NamedNodeMap

    /**
     * Allows for manipulation of element's class content attribute as a set of whitespace-separated tokens through a DOMTokenList object.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/classList)
     */
    val classList: DOMTokenList

    /**
     * Returns the value of element's class content attribute. Can be set to change it.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/className)
     */
    var className: String
    val clientHeight: Int
    val clientLeft: Int
    val clientTop: Int
    val clientWidth: Int

    /**
     * Returns the value of element's id content attribute. Can be set to change it.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/id)
     */
    var id: String

    /**
     * Returns the local name.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/localName)
     */
    val localName: String

    /**
     * Returns the namespace.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/namespaceURI)
     */
    val namespaceURI: String?
    var onfullscreenchange: EventHandler<Event>?
    var onfullscreenerror: EventHandler<Event>?
    var outerHTML: String
    override val ownerDocument: Document
    val part: DOMTokenList

    /**
     * Returns the namespace prefix.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/prefix)
     */
    val prefix: String?
    val scrollHeight: Int
    var scrollLeft: Double
    var scrollTop: Double
    val scrollWidth: Int

    /**
     * Returns element's shadow root, if any, and if shadow root's mode is "open", and null otherwise.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/shadowRoot)
     */
    val shadowRoot: ShadowRoot?

    /**
     * Returns the value of element's slot content attribute. Can be set to change it.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/slot)
     */
    var slot: String

    /**
     * Returns the HTML-uppercased qualified name.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/tagName)
     */
    val tagName: String

    /**
     * Creates a shadow root for element and returns it.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/attachShadow)
     */
    fun attachShadow(init: ShadowRootInit): ShadowRoot
    fun checkVisibility(options: CheckVisibilityOptions = definedExternally): Boolean

    /**
     * Returns the first (starting at element) inclusive ancestor that matches selectors, and null otherwise.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/closest)
     */
    fun <T : HTMLElement> closest(selector: HtmlTagName<T>): T?
    fun <T : SVGElement> closest(selector: SvgTagName<T>): T?
    fun <T : MathMLElement> closest(selector: MathMLTagName<T>): T?
    fun closest(selector: String): Element?
    fun computedStyleMap(): StylePropertyMapReadOnly

    /**
     * Returns element's first attribute whose qualified name is qualifiedName, and null if there is no such attribute otherwise.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/getAttribute)
     */
    fun getAttribute(qualifiedName: String): String?

    /**
     * Returns element's attribute whose namespace is namespace and local name is localName, and null if there is no such attribute otherwise.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/getAttributeNS)
     */
    fun getAttributeNS(
        namespace: String?,
        localName: String,
    ): String?

    /**
     * Returns the qualified names of all element's attributes. Can contain duplicates.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/getAttributeNames)
     */
    fun getAttributeNames(): ReadonlyArray<String>
    fun getAttributeNode(qualifiedName: String): Attr?
    fun getAttributeNodeNS(
        namespace: String?,
        localName: String,
    ): Attr?

    fun getBoundingClientRect(): DOMRect
    fun getClientRects(): DOMRectList

    /**
     * Returns a HTMLCollection of the elements in the object on which the method was invoked (a document or an element) that have all the classes given by classNames. The classNames argument is interpreted as a space-separated list of classes.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/getElementsByClassName)
     */
    fun getElementsByClassName(classNames: String): HTMLCollectionOf<Element>
    fun <T : HTMLElement> getElementsByTagName(qualifiedName: HtmlTagName<T>): HTMLCollectionOf<T>
    fun <T : SVGElement> getElementsByTagName(qualifiedName: SvgTagName<T>): HTMLCollectionOf<T>
    fun <T : MathMLElement> getElementsByTagName(qualifiedName: MathMLTagName<T>): HTMLCollectionOf<T>
    fun getElementsByTagName(qualifiedName: String): HTMLCollectionOf<Element>
    fun <T : SVGElement> getElementsByTagNameNS(
        namespaceURI: SVG_NAMESPACE,
        localName: SvgTagName<T>,
    ): HTMLCollectionOf<T>

    fun <T : MathMLElement> getElementsByTagNameNS(
        namespaceURI: MATHML_NAMESPACE,
        localName: MathMLTagName<T>,
    ): HTMLCollectionOf<T>

    fun getElementsByTagNameNS(
        namespace: String?,
        localName: String,
    ): HTMLCollectionOf<Element>

    /**
     * Returns true if element has an attribute whose qualified name is qualifiedName, and false otherwise.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/hasAttribute)
     */
    fun hasAttribute(qualifiedName: String): Boolean

    /**
     * Returns true if element has an attribute whose namespace is namespace and local name is localName.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/hasAttributeNS)
     */
    fun hasAttributeNS(
        namespace: String?,
        localName: String,
    ): Boolean

    /**
     * Returns true if element has attributes, and false otherwise.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/hasAttributes)
     */
    fun hasAttributes(): Boolean
    fun hasPointerCapture(pointerId: Number): Boolean
    fun insertAdjacentElement(
        where: InsertPosition,
        element: Element,
    ): Element?

    fun insertAdjacentHTML(
        position: InsertPosition,
        text: String,
    )

    fun insertAdjacentText(
        where: InsertPosition,
        data: String,
    )

    /**
     * Returns true if matching selectors against element's root yields element, and false otherwise.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/matches)
     */
    fun matches(selectors: String): Boolean
    fun releasePointerCapture(pointerId: Number)

    /**
     * Removes element's first attribute whose qualified name is qualifiedName.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/removeAttribute)
     */
    fun removeAttribute(qualifiedName: String)

    /**
     * Removes element's attribute whose namespace is namespace and local name is localName.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/removeAttributeNS)
     */
    fun removeAttributeNS(
        namespace: String?,
        localName: String,
    )

    fun removeAttributeNode(attr: Attr): Attr

    /**
     * Displays element fullscreen and resolves promise when done.
     *
     * When supplied, options's navigationUI member indicates whether showing navigation UI while in fullscreen is preferred or not. If set to "show", navigation simplicity is preferred over screen space, and if set to "hide", more screen space is preferred. User agents are always free to honor user preference over the application's. The default value "auto" indicates no application preference.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/requestFullscreen)
     */
    fun requestFullscreen(options: FullscreenOptions = definedExternally): Promise<Void>
    fun requestPointerLock()
    fun scroll(options: ScrollToOptions = definedExternally)
    fun scroll(
        x: Number,
        y: Number,
    )

    fun scrollBy(options: ScrollToOptions = definedExternally)
    fun scrollBy(
        x: Number,
        y: Number,
    )

    fun scrollIntoView(options: ScrollIntoViewOptions = definedExternally)
    fun scrollTo(options: ScrollToOptions = definedExternally)
    fun scrollTo(
        x: Number,
        y: Number,
    )

    /**
     * Sets the value of element's first attribute whose qualified name is qualifiedName to value.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/setAttribute)
     */
    fun setAttribute(
        qualifiedName: String,
        value: String,
    )

    /**
     * Sets the value of element's attribute whose namespace is namespace and local name is localName to value.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/setAttributeNS)
     */
    fun setAttributeNS(
        namespace: String?,
        qualifiedName: String,
        value: String,
    )

    fun setAttributeNode(attr: Attr): Attr?
    fun setAttributeNodeNS(attr: Attr): Attr?
    fun setPointerCapture(pointerId: Number)

    /**
     * If force is not given, "toggles" qualifiedName, removing it if it is present and adding it if it is not present. If force is true, adds qualifiedName. If force is false, removes qualifiedName.
     *
     * Returns true if qualifiedName is now present, and false otherwise.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/Element/toggleAttribute)
     */
    fun toggleAttribute(
        qualifiedName: String,
        force: Boolean = definedExternally,
    ): Boolean
}

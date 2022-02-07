// Automatically generated - do not modify!

package webrtc

import kotlinext.js.ReadonlyArray

external class MediaStream : org.w3c.dom.events.EventTarget {
    //id: string;
    //active: boolean;
    //onactive: EventListener;
    //oninactive: EventListener;
    //onaddtrack: (event: MediaStreamTrackEvent) => any;
    //onremovetrack: (event: MediaStreamTrackEvent) => any;
    fun clone(): MediaStream
    fun stop()
    fun getAudioTracks(): ReadonlyArray<MediaStreamTrack>
    fun getVideoTracks(): ReadonlyArray<MediaStreamTrack>
    fun getTracks(): ReadonlyArray<MediaStreamTrack>
    fun getTrackById(trackId: String): MediaStreamTrack
    fun addTrack(track: MediaStreamTrack)
    fun removeTrack(track: MediaStreamTrack)
}

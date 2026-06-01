package service

import javax.sound.sampled.Clip
import javax.sound.sampled.AudioSystem

/**
 * Implements the Usage of Music and Sounds in the game.
 */
class SoundService {

    private var clip: Clip? = null

    /** Starts the music. */
    fun playBackgroundMusic() {
        val stream = javaClass.getResource("/backgroundMusic.wav")
        val audioInput = AudioSystem.getAudioInputStream(stream)
        clip = AudioSystem.getClip()
        clip?.open(audioInput)
        clip?.loop(Clip.LOOP_CONTINUOUSLY)
        clip?.start()
    }

    /** Stops the music */
    fun stopBackgroundMusic() {
        clip?.stop()
        clip?.flush()
    }
}
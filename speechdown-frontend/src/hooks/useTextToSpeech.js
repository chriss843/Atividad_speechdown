// hooks/useTextToSpeech.js
import { useState, useEffect } from 'react';

export const useTextToSpeech = () => {
  const [isPlaying, setIsPlaying] = useState(false);
  const [audio, setAudio] = useState(null);
  const [currentWord, setCurrentWord] = useState(null);
  const [highlightEnabled, setHighlightEnabled] = useState(true);

  // Para Web Speech API (nativo)
  const speakNative = (text) => {
    if ('speechSynthesis' in window) {
      const utterance = new SpeechSynthesisUtterance(text);
      utterance.lang = 'es-ES';
      utterance.rate = 0.9;

      if (highlightEnabled) {
        const words = text.split(' ');
        let currentIndex = 0;
        
        utterance.onboundary = (event) => {
          if (event.name === 'word') {
            setCurrentWord(currentIndex);
            currentIndex++;
          }
        };
      }

      utterance.onstart = () => setIsPlaying(true);
      utterance.onend = utterance.onerror = () => {
        setIsPlaying(false);
        setCurrentWord(null);
      };

      window.speechSynthesis.speak(utterance);
      return () => window.speechSynthesis.cancel();
    } else {
      alert('La síntesis de voz no está soportada en tu navegador');
    }
  };

  // Para Google TTS (backend)
  const speakWithGoogleTTS = async (text) => {
    try {
      const response = await fetch('http://localhost:8080/api/speech/synthesize', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ text })
      });
      
      const audioBlob = await response.blob();
      const audioUrl = URL.createObjectURL(audioBlob);
      const newAudio = new Audio(audioUrl);
      
      newAudio.onplay = () => setIsPlaying(true);
      newAudio.onended = newAudio.onpause = () => {
        setIsPlaying(false);
        setCurrentWord(null);
      };
      
      setAudio(newAudio);
      newAudio.play();

      if (highlightEnabled) {
        const words = text.split(' ');
        words.forEach((_, i) => {
          setTimeout(() => setCurrentWord(i), i * 300); // Ajusta timing
        });
      }
    } catch (error) {
      console.error('Error al sintetizar voz:', error);
      // Fallback a nativo si hay error
      speakNative(text);
    }
  };

  const stop = () => {
    if (audio) {
      audio.pause();
      audio.currentTime = 0;
    }
    window.speechSynthesis.cancel();
    setIsPlaying(false);
    setCurrentWord(null);
  };

  useEffect(() => {
    return () => {
      if (audio) {
        audio.pause();
      }
      window.speechSynthesis.cancel();
    };
  }, [audio]);

  return { 
    speak: speakWithGoogleTTS, 
    speakNative,
    stop, 
    isPlaying, 
    currentWord,
    toggleHighlight: () => setHighlightEnabled(!highlightEnabled),
    highlightEnabled
  };
};
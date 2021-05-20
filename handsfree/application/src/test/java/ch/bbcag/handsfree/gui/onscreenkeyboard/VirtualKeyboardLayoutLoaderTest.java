package ch.bbcag.handsfree.gui.onscreenkeyboard;

import ch.bbcag.handsfree.control.HandsFreeKeyCodes;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class VirtualKeyboardLayoutLoaderTest {

    @ParameterizedTest
    @MethodSource("getKeyCodeArgs")
    public void when_load_keyboard_keys_codes_are_correct(int row, int column, int keyCode) {
        VirtualKeyboardLayout layout = VirtualKeyboardLayoutLoader.loadFromResource("/keyboard_layout.txt");
        VirtualKey key = layout.getKeyRows()[row].getKeys()[column];
        assertEquals(key.getKeyCode(), keyCode);
    }

    private static Stream<Arguments> getKeyCodeArgs() {
        return Stream.of(
                Arguments.arguments(2, 3, HandsFreeKeyCodes.E),
                Arguments.arguments(4, 6, HandsFreeKeyCodes.B),
                Arguments.arguments(4, 0, HandsFreeKeyCodes.LSHIFT),
                Arguments.arguments(1, 15, HandsFreeKeyCodes.HOME)
        );
    }

}
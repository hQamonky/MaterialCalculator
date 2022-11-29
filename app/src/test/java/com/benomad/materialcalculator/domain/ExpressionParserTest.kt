package com.benomad.materialcalculator.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ExpressionParserTest {
    private lateinit var parser: ExpressionParser

    @Test
    fun `Simple expression is properly parsed`() {
        // 1. GIVEN
        parser = ExpressionParser("3+5-3x4/3")

        // 2. DO SOMETHING
        val parts = parser.parse()

        // 3. ASSERT EXPECTED == ACTUAL
        val expected = listOf(
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Number(5.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.DIVIDE),
            ExpressionPart.Number(3.0)
        )
        assertThat(parts).isEqualTo(expected)
    }

    @Test
    fun `Expression with parentheses is properly parsed`() {
        parser = ExpressionParser("4-(4x5)")

        val parts = parser.parse()

        val expected = listOf(
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(5.0),
            ExpressionPart.Parentheses(ParenthesesType.Closing)
        )
        assertThat(parts).isEqualTo(expected)
    }

    @Test
    fun `Expression with decimal numbers is properly parsed`() {
        parser = ExpressionParser("4.55-0.12x5")

        val parts = parser.parse()

        val expected = listOf(
            ExpressionPart.Number(4.55),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Number(0.12),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(5.0)
        )
        assertThat(parts).isEqualTo(expected)
    }

    @Test
    fun `Expression with big numbers and parentheses is properly parsed`() {
        parser = ExpressionParser("(2022-1992)/2+7")

        val parts = parser.parse()

        val expected = listOf(
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(2022.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Number(1992.0),
            ExpressionPart.Parentheses(ParenthesesType.Closing),
            ExpressionPart.Op(Operation.DIVIDE),
            ExpressionPart.Number(2.0),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Number(7.0)
        )
        assertThat(parts).isEqualTo(expected)
    }

    @Test
    fun `Expression with invalid characters is properly parsed`() {
        parser = ExpressionParser("3&+5v-3x}4/3")

        val parts = parser.parse()

        val expected = listOf(
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Number(5.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.DIVIDE),
            ExpressionPart.Number(3.0)
        )
        assertThat(parts).isEqualTo(expected)
    }
}
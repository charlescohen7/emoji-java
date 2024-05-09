package com.vdurmont.emoji;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * This class represents an emoji.<br>
 * <br>
 * This object is immutable so it can be used safely in a multithreaded context.
 *
 * @author Vincent DURMONT [vdurmont@gmail.com]
 */
public class Emoji {
  private String description;
  private boolean supportsFitzpatrick;
  private boolean emoji_used_for_spam;
  private boolean is_sex_sign;
  private List<String> aliases;
  private List<String> tags;
  private String sex_sign_unicode;
  private String unicode;

  protected Emoji() {

  }

  /**
   * Constructor for the Emoji.
   *
   * @param description         The description of the emoji
   * @param supportsFitzpatrick Whether the emoji supports Fitzpatrick modifiers
   * @param aliases             the aliases for this emoji
   * @param tags                the tags associated with this emoji
   * @param bytes               the bytes that represent the emoji
   */


  protected Emoji(
    String description,
    boolean supportsFitzpatrick,
    List<String> aliases,
    List<String> tags,
    boolean emoji_used_for_spam,
    boolean is_sex_sign,
    byte... bytes
  ) {
    this.description = description;
    this.supportsFitzpatrick = supportsFitzpatrick;
    this.emoji_used_for_spam = emoji_used_for_spam;
    this.is_sex_sign = is_sex_sign;
    this.aliases = Collections.unmodifiableList(aliases);
    this.tags = Collections.unmodifiableList(tags);
    this.sex_sign_unicode = "";
    this.unicode = new String(bytes, StandardCharsets.UTF_8);
  }

  public void addSexSign(String sex_sign_unicode) {
    this.sex_sign_unicode = sex_sign_unicode;
  }

  public boolean isMaleEmoji() {
    return (this.unicode + this.sex_sign_unicode).contains("♂") || (this.description != null && (this.description.startsWith("man") || (this.description.endsWith("man") && !this.description.contains("woman"))));
  }

  public boolean isFemaleEmoji() {
    return (this.unicode + this.sex_sign_unicode).contains("♀") || (this.description != null && (this.description.startsWith("woman") || this.description.endsWith("woman")));
  }

  /**
   * Returns the description of the emoji
   *
   * @return the description
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Returns wether the emoji supports the Fitzpatrick modifiers or not
   *
   * @return true if the emoji supports the Fitzpatrick modifiers
   */
  public boolean supportsFitzpatrick() {
    return this.supportsFitzpatrick;
  }

  public boolean isEmojiUsedForSpam() {
    return this.emoji_used_for_spam;
  }

  public boolean isSexSign() {
    return this.is_sex_sign;
  }

  /**
   * Returns the aliases of the emoji
   *
   * @return the aliases (unmodifiable)
   */
  public List<String> getAliases() {
    return this.aliases;
  }

  /**
   * Returns the tags of the emoji
   *
   * @return the tags (unmodifiable)
   */
  public List<String> getTags() {
    return this.tags;
  }

  /**
   * Returns the unicode representation of the emoji
   *
   * @return the unicode representation
   */
  public String getUnicode() {

    if (this.sex_sign_unicode.isEmpty()) {
      return this.unicode;
    }

    return this.unicode + "\u200D" + this.sex_sign_unicode;

  }

  public String getUnicodeWithoutSexSign() {
    return this.unicode;
  }

  /**
   * Returns the unicode representation of the emoji associated with the
   * provided Fitzpatrick modifier.<br>
   * If the modifier is null, then the result is similar to
   * {@link Emoji#getUnicode()}
   *
   * @param fitzpatrick the fitzpatrick modifier or null
   *
   * @return the unicode representation
   * @throws UnsupportedOperationException if the emoji doesn't support the
   * Fitzpatrick modifiers
   */
  public String getUnicode(Fitzpatrick fitzpatrick) {
    if (!this.supportsFitzpatrick()) {
      throw new UnsupportedOperationException(
        "Cannot get the unicode with a fitzpatrick modifier, " +
        "the emoji doesn't support fitzpatrick."
      );
    } else if (fitzpatrick == null) {
      return this.getUnicode();
    }
    return this.getUnicodeWithoutSexSign() + fitzpatrick.unicode + this.sex_sign_unicode;
  }

  @Override
  public boolean equals(Object other) {
    return !(other == null || !(other instanceof Emoji)) &&
      ((Emoji) other).getUnicode().equals(getUnicode());
  }

  @Override
  public int hashCode() {
    return getUnicode().hashCode();
  }

  /**
   * Returns the String representation of the Emoji object.<br>
   * <br>
   * Example:<br>
   * <code>Emoji {
   *   description='smiling face with open mouth and smiling eyes',
   *   supportsFitzpatrick=false,
   *   aliases=[smile],
   *   tags=[happy, joy, pleased],
   *   unicode='😄',
   *   htmlDec='&amp;#128516;',
   *   htmlHex='&amp;#x1f604;'
   * }</code>
   *
   * @return the string representation
   */
  @Override
  public String toString() {
    return "Emoji{" +
      "description='" + description + '\'' +
      ", supportsFitzpatrick=" + supportsFitzpatrick +
      ", aliases=" + aliases +
      ", tags=" + tags +
      ", unicode='" + this.getUnicode() + '\'' +
      '}';
  }

  public Emoji copy() {

    Emoji emoji = new Emoji();

    emoji.description = this.description;
    emoji.supportsFitzpatrick = this.supportsFitzpatrick;
    emoji.emoji_used_for_spam = this.emoji_used_for_spam;
    emoji.is_sex_sign = this.is_sex_sign;
    emoji.aliases = this.aliases;
    emoji.tags = this.tags;
    emoji.sex_sign_unicode = this.sex_sign_unicode;
    emoji.unicode = this.unicode;

    return emoji;

  }

}

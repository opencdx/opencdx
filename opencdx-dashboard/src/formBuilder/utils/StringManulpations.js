/*
 *  Capitalize the first letter of each word
 * Search for ' Anf ' (case-insensitive) and update it to ' ANF '
 */

export function capitalizeANFTitle(str) {
    str = str.replace(/\b\w/g, (match) => match.toUpperCase());
    str = str.replace(/\bAnf\b/gi, 'ANF');

    return str;
}

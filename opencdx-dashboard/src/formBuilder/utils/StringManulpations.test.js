import { capitalizeANFTitle } from './StringManulpations';

describe('capitalizeANFTitle', () => {
  it('capitalizes the first letter of each word', () => {
    const input = 'hello world';
    const expectedOutput = 'Hello World';
    const result = capitalizeANFTitle(input);
    expect(result).toEqual(expectedOutput);
  });

    it('does not capitalize the first letter of words that are already capitalized', () => {
        const input = 'Hello World';
        const expectedOutput = 'Hello World';
        const result = capitalizeANFTitle(input);
        expect(result).toEqual(expectedOutput);
    });
    
});
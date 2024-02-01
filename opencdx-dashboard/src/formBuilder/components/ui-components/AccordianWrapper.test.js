import React from 'react';
import renderer from 'react-test-renderer';
import {AccordianWrapper} from './AccordianWrapper';

it('renders correctly', () => {
    const tree = renderer.create(<AccordianWrapper />).toJSON();
    expect(tree).toMatchSnapshot();
});
it('renders correctly with title', () => {
    const tree = renderer.create(<AccordianWrapper title="Test Title" />).toJSON();
    expect(tree).toMatchSnapshot();
} );
it('renders correctly with children', () => {
    const tree = renderer.create(<AccordianWrapper><div>Test</div></AccordianWrapper>).toJSON();
    expect(tree).toMatchSnapshot();
} );
it('renders correctly with title and children', () => {
    const tree = renderer.create(<AccordianWrapper title="Test Title"><div>Test</div></AccordianWrapper>).toJSON();
    expect(tree).toMatchSnapshot();
} );
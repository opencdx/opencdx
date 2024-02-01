import React from 'react';
import { render } from '@testing-library/react';
import {InputLabel} from './InputLabel';

describe('BInputLabel', () => {
  it('renders correctly', () => {
    render(<InputLabel />);
    // Add your assertions here
  });

  it('applies the correct styles when horizontal prop is true', () => {
    render(<InputLabel horizontal />);
    // Add your assertions here
  });

  it('applies the correct styles when horizontal prop is false', () => {
    render(<InputLabel horizontal={false} />);
    // Add your assertions here
  });
});
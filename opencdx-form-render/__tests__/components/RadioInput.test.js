import React, { useState } from 'react';
import renderer from 'react-test-renderer';
import { RadioInput } from '../../components/RadioInput';

describe('RadioInput', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  const answerOption = [
    {
      id: 1,
      label: 'Yes',
      value: 'yes',
    },
    {
      id: 2,
      label: 'No',
      value: 'no',
    },
    {
      id: 3,
      label: 'Not Answered',
      value: 'not answered',
    }
  ];

  it('renders all answer options correctly', () => {
    const setFormErrorMock = jest.fn();

    const component = renderer.create(<RadioInput options={answerOption} setFormError={setFormErrorMock} />);
    const tree = component.toJSON();
    expect(tree).toMatchSnapshot();
  });
  
});

  
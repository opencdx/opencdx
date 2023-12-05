/*
 * Copyright (c) Facebook, Inc. and its affiliates.
 */

import * as React from 'react';

// import { useHistory } from 'react-router-dom';

export function HomeContent() {
  const [isOpen, setIsOpen] = React.useState(true);

  // const history = useHistory();

  const handleSubmit = event => {
    event.preventDefault();

    // 👇️ redirect to /contacts
    // history.push('/admin/dashboard');
  };
  return (
    <>
      <section>
        <body class="bg-gray-200 font-sans text-gray-700">
          <div class="container mx-auto p-8 flex">
            <div class="max-w-md w-full mx-auto">

{isOpen?<><div class="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
              <div class="p-6 space-y-4 md:space-y-6 sm:p-8">
                <h1 class="text-xl font-bold leading-tight tracking-tight  md:text-2xl dark:text-white">
                  Login
                </h1>
                <form onSubmit={handleSubmit}>
                  <div>
                    <label for="email" class="block mb-2 text-sm font-medium ">Your email</label>
                    <input type="email" name="email" id="email" class=" border border-gray-300  sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="name@company.com" required="" />
                  </div>
                  <div>
                    <label for="password" class="block mb-2 text-sm font-medium ">Password</label>
                    <input type="password" name="password" id="password" placeholder="••••••••" class=" border border-gray-300  sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required="" />
                  </div>

                    <button class="w-full p-3 mt-4 bg-indigo-600 text-white rounded shadow">Login</button>
                  </form>
                </div>
                <div class="flex justify-between p-8 text-sm border-t border-gray-300 bg-gray-100">
                  <a href="#" class="font-medium text-indigo-500">Create account</a>

                  <a href="#" class="text-gray-600">Forgot password?</a>
                </div>
              </div></>:<><div class="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
              <div class="p-6 space-y-4 md:space-y-6 sm:p-8">
                <h1 class="text-xl font-bold leading-tight tracking-tight  md:text-2xl dark:text-white">
                  Create and account
                </h1>
                <form onSubmit={handleSubmit}>
                  <div>
                    <label for="email" class="block mb-2 text-sm font-medium ">Your email</label>
                    <input type="email" name="email" id="email" class=" border border-gray-300  sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="name@company.com" required="" />
                  </div>
                  <div>
                    <label for="password" class="block mb-2 text-sm font-medium ">Password</label>
                    <input type="password" name="password" id="password" placeholder="••••••••" class=" border border-gray-300  sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required="" />
                  </div>
                  <div>
                    <label for="confirm-password" class="block mb-2 text-sm font-medium ">Confirm password</label>
                    <input type="confirm-password" name="confirm-password" id="confirm-password" placeholder="••••••••" class=" border border-gray-300  sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required="" />
                  </div>
                  <button class="w-full p-3 mt-4 bg-indigo-600 text-white rounded shadow">Register</button>

                  {/* <button type="submit" class="w-full text-white bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800">Create an account</button> */}
                  <p class="text-sm font-light text dark:text-gray-400">
                    Already have an account? <a href="#" class="font-medium text-primary-600 hover:underline dark:text-primary-500">Login here</a>
                  </p>
                </form>
              </div>
            </div></>}
            
            </div>
            
          </div>
        </body>
       
      </section>
      <section class=" dark:bg-gray-900">
          <div class="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
            
            
          </div>
        </section>
    </>
  );
}
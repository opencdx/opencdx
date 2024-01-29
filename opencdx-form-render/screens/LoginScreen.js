import React, { useState } from 'react';
import { View, StyleSheet, Platform, SafeAreaView } from 'react-native';
import axios from '../utils/axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import {
    Button, Heading, Input, ButtonText, InputField, Image, Link, LinkText, Text,
    InputSlot,
    InputIcon,
    EyeIcon,
    EyeOffIcon,
    Switch,

} from '@gluestack-ui/themed';

const LoginScreen = ({ navigation }) => {
    const [username, setUsername] = useState('admin@opencdx.org');
    const [password, setPassword] = useState('password');
    const [showPassword, setShowPassword] = useState(false)
    const handleState = () => {
        setShowPassword((showState) => {
            return !showState
        })
    }

    const handleLogin = async () => {
        try {
            const response = await axios.post('/iam/user/login', { userName: username, password: password });
            await AsyncStorage.setItem('jwtToken', response.data.token);
            navigation.navigate('List');
        } catch (error) {
            alert('Invalid credentials');
        }
    };
    const [rememberMe, setRememberMe] = useState(false);
    const toggleRememberMe = (value) => {
        setRememberMe(value);
    };


    return (
        <SafeAreaView style={styles.container}>
            <View style={styles.body}>
                <Image
                    size="md"
                    resizeMode="contain"
                    alt="OpenCDX logo"
                    style={styles.image}
                    source={require('../assets/Open.png')}
                />
                <Input
                    onChangeText={setUsername}
                    style={styles.input}
                    variant="underlined"
                    size="md"
                >
                    <InputField placeholder="Username" defaultValue={username} />
                </Input>
                <Input
                    onChangeText={setPassword}
                    style={styles.input}
                    variant="underlined"
                    size="md"

                >
                    <InputField type={showPassword ? "text" : "password"} placeholder="Password" defaultValue={password} />
                    <InputSlot pr="$3" onPress={handleState}>
                        <InputIcon
                            as={showPassword ? EyeIcon : EyeOffIcon}
                            color="$darkBlue500"
                        />
                    </InputSlot>
                </Input>
                <View style={styles.switch}>
                    <Switch
                        value={rememberMe}
                        onValueChange={(value) => toggleRememberMe(value)}

                    /><Text style={{ marginLeft: 10 }}
                    >Remember Me</Text>
                </View>
                <Link href="" style={styles.forget}>
                    <LinkText>Forget Password?</LinkText>
                </Link>
            </View>
            <View style={styles.footer}>
                <Button title="Sign In" onPress={handleLogin} style={styles.button}>
                    <ButtonText style={styles.buttonText}>Sign In</ButtonText>
                </Button>
                <Link href="" style={styles.signup}>
                    <LinkText>Don't have an account? Sign Up</LinkText>
                </Link>
            </View>
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        shadowColor: "#000",
        margin: 'auto',
        ...Platform.select({
            web: {
                minWidth: 500,
                justifyContent: 'center',
            },
            default: {
                justifyContent: 'space-between',
            }
        })
    },
    switch: {
        flexDirection: 'row',
        alignItems: 'center',
        marginBottom: 10,
    },
    center: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
    },
    centerText: {
        marginRight: 5,
    },
    input: {
        marginBottom: 10,
    },
    button: {
        marginBottom: 10,
        alignItems: 'center',
        justifyContent: 'center',
        borderRadius: 20,
        padding: 8,
        backgroundColor: '#007bff',
        ...Platform.select({
            web: {
                width: 500,
            },
            default: {
                width: '90%',
            }
        })
    },
    buttonText: {
        color: 'white',
    },
    image: {
        marginBottom: 5,
        justifyContent: 'center',
        width: 500,
    },
    forget: {
        alignItems: 'flex-end',
        marginBottom: 10,
        marginTop: 10,
        textAlign: 'right',
        flexDirection: "row",
        justifyContent: "flex-end",
    },
    signup: {
        marginBottom: 10,
        textAlign: 'right',
        flexDirection: "row",
        justifyContent: "center",
    },
    body: {
        padding: 8,
        ...Platform.select({
            web: {

            },
            default: {
                flex: 1,
                justifyContent: 'center',
                margin:20,
                shadowColor: "#000",   
            }
        })
    },
    footer: {
        alignContent: 'center',
        justifyContent: 'center',
        alignItems: 'center',
        padding: 8,
        alignItems: 'center',
        justifyContent: 'center',
        borderRadius: 4,
        padding: 8,
    },
});

export default LoginScreen;
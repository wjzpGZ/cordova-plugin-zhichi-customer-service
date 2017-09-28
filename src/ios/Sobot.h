#import <Cordova/CDV.h>

@interface Sobot : CDVPlugin

- (void)setUserInfo:(CDVInvokedUrlCommand *)command;
- (void)open:(CDVInvokedUrlCommand *)command;
- (void)logout:(CDVInvokedUrlCommand *)command;

@end

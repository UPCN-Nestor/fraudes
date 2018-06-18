/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FrTestModule } from '../../../test.module';
import { EstadoMySuffixComponent } from '../../../../../../main/webapp/app/entities/estado-my-suffix/estado-my-suffix.component';
import { EstadoMySuffixService } from '../../../../../../main/webapp/app/entities/estado-my-suffix/estado-my-suffix.service';
import { EstadoMySuffix } from '../../../../../../main/webapp/app/entities/estado-my-suffix/estado-my-suffix.model';

describe('Component Tests', () => {

    describe('EstadoMySuffix Management Component', () => {
        let comp: EstadoMySuffixComponent;
        let fixture: ComponentFixture<EstadoMySuffixComponent>;
        let service: EstadoMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [EstadoMySuffixComponent],
                providers: [
                    EstadoMySuffixService
                ]
            })
            .overrideTemplate(EstadoMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EstadoMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstadoMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new EstadoMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.estados[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
